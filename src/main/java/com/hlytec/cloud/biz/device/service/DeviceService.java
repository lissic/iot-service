package com.hlytec.cloud.biz.device.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import cn.dev33.satoken.stp.StpUtil;
import com.hlytec.cloud.biz.report.model.po.DeviceAlarm;
import com.hlytec.cloud.biz.system.user.model.entity.User;
import com.hlytec.cloud.biz.system.user.utils.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlytec.cloud.biz.alarm.mapper.AlarmMapper;
import com.hlytec.cloud.biz.alarm.model.entity.Alarm;
import com.hlytec.cloud.biz.device.mapper.DeviceConfigMapper;
import com.hlytec.cloud.biz.device.mapper.DeviceMapper;
import com.hlytec.cloud.biz.device.model.entity.Device;
import com.hlytec.cloud.biz.device.model.entity.DeviceMonitor;
import com.hlytec.cloud.biz.device.model.po.DeviceConfigPo;
import com.hlytec.cloud.biz.device.model.vo.QueryDeviceVo;
import com.hlytec.cloud.biz.report.model.po.DeviceNumWithStationPo;
import com.hlytec.cloud.biz.system.station.model.entity.Station;
import com.hlytec.cloud.biz.system.station.service.StationService;
import com.hlytec.cloud.common.constants.SysConstants;
import com.hlytec.cloud.common.entity.CommonParamVo;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.result.PageResult;
import com.hlytec.cloud.common.result.ResultEnum;
import com.hlytec.cloud.common.service.BaseService;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

/**
 * @description: DeviceService
 * @author: zero
 * @date: 2021/5/31 11:21
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class DeviceService extends BaseService<DeviceMapper, Device> {

    private static final List<Integer> DEVICE_STATUS = Arrays.asList(0,1,2,3,4,5,99);
    private static final List<Integer> ALARM_LEVEL = Arrays.asList(0,1,2,3);
    private final String[] volLevels = new String[]{"48V","110V","220V"};
    private final String[] dischargeTypes = new String[]{"????????????","????????????","PTC??????","???????????????"};

    @Autowired
    private DeviceConfigMapper deviceConfigMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private StationService stationService;

    @Autowired
    private AlarmMapper alarmMapper;

    @Autowired
    private DeviceMonitorService monitorService;

    @Transactional(readOnly = false)
    public String saveDevice(Device device) {
        Device exits = getDeviceByCardId(device.getCardId());
        if (Objects.nonNull(exits)) {
            throw new NetServiceException(ResultEnum.DEVICE_CARDID_IS_EXITS);
        }
        String id = save(device);
        DeviceMonitor deviceMonitor = DeviceMonitor.builder().deviceId(id).build();
        monitorService.saveOrUpdate(deviceMonitor);
        return id;
    }

    public Device getDevice(String deviceId){
        Device device =this.get(deviceId);
        if (Objects.nonNull(device)){
            List<DeviceConfigPo> config = deviceConfigMapper.getConfig(deviceId);
            device.setConfigList(config);
        }
        return device;
    }

    @Transactional(readOnly = false)
    public void deleteDevice(CommonParamVo deleteDeviceVo){
        List<String> deviceIds = deleteDeviceVo.getIds();
        deviceIds.forEach(deviceId->{
            List<String> configIds = deviceConfigMapper.getConfig(deviceId).stream()
                    .map(DeviceConfigPo::getId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(configIds)) {
                deviceConfigMapper.deleteBatchIds(configIds);
            }
        });
        this.batchDelete(deviceIds);
    }

    public List<Device> findDeviceList(QueryDeviceVo queryDeviceVo, String token) {
        QueryWrapper<Device> queryWrapper = getDeviceQueryWrapper(queryDeviceVo, token);
        List<Device> devices = deviceMapper.selectList(queryWrapper);
        return devices;
    }

    public PageResult<Device> findDevicePage(QueryDeviceVo queryDeviceVo, String token) {
        QueryWrapper<Device> queryWrapper = getDeviceQueryWrapper(queryDeviceVo, token);
        Page<Device> page = new Page<>(queryDeviceVo.getPageNo(), queryDeviceVo.getPageSize());
        Page<Device> devicePage = deviceMapper.selectPage(page, queryWrapper);
        PageResult<Device> result = new PageResult<>(devicePage);
        return result;
    }

    private QueryWrapper<Device> getDeviceQueryWrapper(QueryDeviceVo queryDeviceVo, String token) {
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(queryDeviceVo.getCardId())) {
            queryWrapper.eq("card_id", queryDeviceVo.getCardId());
        }
        if (StringUtils.isNotEmpty(queryDeviceVo.getName())) {
            queryWrapper.like("name", queryDeviceVo.getName());
        }
        // ???????????????????????????admin,area????????????????????????????????????
        User userCache = UserUtil.getUserCache(token);
        if (!StringUtils.equals(userCache.getId(), SysConstants.ADMIN_ID)) {
            List<String> stations = stationService.findList(Station.builder().area(userCache.getArea()).build())
                    .stream().map(Station::getId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(stations)) {
                throw new NetServiceException(ResultEnum.NO_DATA_ACCESS_AUTH);
            }
            queryWrapper.in("station_id", stations);
        }
        if (CollectionUtils.isNotEmpty(queryDeviceVo.getStationIds())) {
            queryWrapper.in("station_id", queryDeviceVo.getStationIds());
        }
        if (StringUtils.isNotEmpty(queryDeviceVo.getDevType())) {
            queryWrapper.eq("dev_type", queryDeviceVo.getDevType());
        }
        if (Objects.nonNull(queryDeviceVo.getDischargeType())) {
            queryWrapper.eq("discharge_type", queryDeviceVo.getDischargeType());
        }
        if (Objects.nonNull(queryDeviceVo.getVolLevel())) {
            queryWrapper.eq("vol_level", queryDeviceVo.getVolLevel());
        }
        if (Objects.nonNull(queryDeviceVo.getStatus())) {
            queryWrapper.eq("status", queryDeviceVo.getStatus());
        }
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }

    /**
     * ??????????????????????????????
     * @return List<Map<String,Integer>>
     */
    public List<Map<String,Integer>> getNumWithStatus() {
        User currentUser = UserUtil.getCurrentUser();
        String loginName = currentUser.getLoginName();
        String area = currentUser.getArea();
        List<Map<String, Integer>> numWithStatus = deviceMapper.getNumWithStatus(loginName,area);
        fillData(numWithStatus, 6, "status", DEVICE_STATUS);
        return numWithStatus;
    }

    /**
     * ????????????????????????????????????
     * @return List<DeviceNumWithAreaPo>
     */
    public List<DeviceNumWithStationPo> getNumWithStation() {
        User currentUser = UserUtil.getCurrentUser();
        String loginName = currentUser.getLoginName();
        String area = currentUser.getArea();
        return deviceMapper.getNumWithStation(loginName,area);
    }

    /**
     * ??????????????????????????????????????????
     * @param alarmType alarmType
     * @return List<Map<String, Integer>>
     */
    public List<Map<String, Integer>> getNumWithAlarm(Integer alarmType) {
        // ????????????
        if (alarmType == 0) {
            List<Map<String, Integer>> sysAlarm = deviceMapper.getNumWithSysAlarm();
            fillData(sysAlarm, 4, "level", ALARM_LEVEL);
            return sysAlarm;
        }
        if (alarmType == 1) {
            List<Map<String, Integer>> devAlarm = deviceMapper.getNumWithDevAlarm();
            fillData(devAlarm, 4, "level", ALARM_LEVEL);
            return devAlarm;
        }
        return null;
    }

    public List<DeviceAlarm> getAlarmByDevice() {
        User currentUser = UserUtil.getCurrentUser();
        String loginName = currentUser.getLoginName();
        String area = currentUser.getArea();
        List<DeviceAlarm> alarmByDevice = deviceMapper.getAlarmByDevice(loginName, area);
        return alarmByDevice.stream().sorted(Comparator.comparing(DeviceAlarm::getNum).reversed())
            .collect(Collectors.toList());
    }

    /**
     * ?????????????????????????????????
     * @param alarmType alarmType
     * @return Map<String,Long>
     */
    public Map<String,Long> alarmMsgByTime(Integer alarmType) {
        User currentUser = UserUtil.getCurrentUser();
        String area = currentUser.getArea();
        Map<String,Long> result = new LinkedHashMap<>(15);
        QueryWrapper<Alarm> query = new QueryWrapper<>();
        Date now = new Date();
        Date before = new Date(now.getTime()-1000*60*60);
        QueryWrapper<Alarm> alarmTime = query.between("alarm_time", before, now);
        // ????????????
        if (alarmType == 0) {
            query.in("device_id", SysConstants.SYSTEM_CODE);
        }
        // ????????????
        if (alarmType == 1) {
            if (StringUtils.equals(SysConstants.ADMIN_ID,currentUser.getId())) {
                query.notIn("device_id", SysConstants.SYSTEM_CODE);
            } else {
                List<Station> list = stationService.findList(Station.builder().area(area).build());
                if (CollectionUtils.isNotEmpty(list)) {
                    List<String> devIds = deviceMapper.getDeviceByArea(area);
                    query.in("device_id", devIds);
                } else {
                    return result;
                }
            }
        }
        List<Alarm> alarms = alarmMapper.selectList(alarmTime);
        // ????????????????????????15??????4?????????
        for (int i = 0; i < 16; i++) {
            Date start = before;
            Date after = new Date(before.getTime() + 1000 * 60 * 4);
            long count = alarms.stream().filter(alarm -> alarm.getAlarmTime().after(start) && alarm.getAlarmTime().before(after)).count();
            result.put(before.getHours()+"???"+before.getMinutes()+"???",count);
            before=after;
        }
        return result;
    }

    private void fillData(List<Map<String, Integer>> data, int size, String property, List<Integer> dict) {
        List<Integer> temp = new ArrayList<>(size);
        data.forEach(map -> {
            Integer level = map.get(property);
            temp.add(level);
        });
        dict.forEach(val -> {
            if (!temp.contains(val)) {
                Map<String, Integer> other = new HashMap<>(1);
                other.put(property, val);
                other.put("total", 0);
                data.add(other);
            }
        });
    }

    /**
     * ????????????????????????
     * @param cardId cardId
     * @return Device
     */
    public Device getDeviceByCardId(String cardId) {
        if (StringUtils.isNotEmpty(cardId)) {
            return deviceMapper.getDeviceByCardId(cardId);
        }
        return null;
    }

    /**
     * ????????????????????????
     * @param response response
     * @throws IOException IOException
     */
    public void downloadExcel(HttpServletResponse response) throws IOException {
        Map<String, Object> row = new HashMap<>(7);
        row.put("no","");
        row.put("name", "");
        row.put("cardId", "");
        row.put("devType", "");
        row.put("volLevel", "");
        row.put("dischargeType", "");
        row.put("areaId", "");
        List<Map<String, Object>> rows = Collections.singletonList(row);
        List<String> list = stationService.findList(null).stream().map(Station::getName).collect(Collectors.toList());
        String[] size = new String[list.size()];
        String[] areas = list.toArray(size);
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.merge(6, "??????????????????");
        writer.addSelect(new CellRangeAddressList(2,100,6,6), areas);
        writer.addSelect(new CellRangeAddressList(2,100,5,5), dischargeTypes);
        writer.addSelect(new CellRangeAddressList(2,100,4,4), volLevels);
        writer.addHeaderAlias("no", "??????");
        writer.addHeaderAlias("name", "????????????");
        writer.addHeaderAlias("cardId", "????????????");
        writer.addHeaderAlias("devType", "????????????");
        writer.addHeaderAlias("volLevel", "????????????");
        writer.addHeaderAlias("dischargeType", "????????????");
        writer.addHeaderAlias("areaId", "????????????");
        writer.write(rows, true);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=????????????.xls");
        ServletOutputStream out=response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
    }

    /**
     * ??????????????????
     * @param inputStream inputStream
     */
    @Transactional(readOnly = false)
    public void batchSave(InputStream inputStream) {
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Map<String, Object>> devs = reader.read(1,2,Integer.MAX_VALUE);
        if (CollectionUtils.isEmpty(devs)) {
            throw new NetServiceException();
        }
        devs.parallelStream().forEach(dev -> {
            Device device = buildDevice(dev);
            if (Objects.isNull(device)) {
                throw new NetServiceException(ResultEnum.BATCH_SAVE_FAILED);
            } else {
                String devId = save(device);
                DeviceMonitor deviceMonitor = DeviceMonitor.builder().deviceId(devId).build();
                monitorService.saveOrUpdate(deviceMonitor);
            }
        });
    }

    private Device buildDevice(Map<String, Object> dev) {
        Device device = Device.builder().build();
        String name = String.valueOf(dev.get("????????????"));
        String cardId = String.valueOf(dev.get("????????????"));
        String devType = String.valueOf(dev.get("????????????"));
        String volLevel = String.valueOf(dev.get("????????????"));
        String dischargeType = String.valueOf(dev.get("????????????"));
        String stationName = String.valueOf(dev.get("????????????"));
        Station station = stationService.get(Station.builder().name(stationName).build());
        Device exits = get(Device.builder().name(name).build());
        // ????????????
        if (Objects.isNull(station) || StringUtils.isEmpty(name) || StringUtils.isEmpty(cardId)
            || StringUtils.isEmpty(devType) || StringUtils.isEmpty(volLevel) || StringUtils.isEmpty(dischargeType)
            || cardId.length() != 15 || Objects.nonNull(exits)) {
            return null;
        }
        device.setStationId(station.getId());
        device.setName(name);
        device.setCardId(cardId);
        device.setDevType(devType);
        device.setVolLevel(getVolLevel(volLevel));
        device.setDischargeType(getDischargeType(dischargeType));
        return device;
    }

    /**
     * ????????????:0-220V;1-110V;2-48V
     * @param volLevel volLevel
     * @return Integer
     */
    private Integer getVolLevel(String volLevel) {
        switch (volLevel) {
            case "110V":
                return 1;
            case "220V":
                return 0;
            default:
                return 2;
        }
    }

    /**
     * ????????????: 0-????????????;1-????????????;2-PTC??????;3-???????????????
     * @param dischargeType dischargeType
     * @return Integer
     */
    private Integer getDischargeType(String dischargeType) {
        switch (dischargeType) {
            case "????????????":
                return 0;
            case "PTC??????":
                return 2;
            case "???????????????":
                return 3;
            default:
                return 1;
        }
    }
}
