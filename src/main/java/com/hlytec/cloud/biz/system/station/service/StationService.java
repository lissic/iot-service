package com.hlytec.cloud.biz.system.station.service;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlytec.cloud.biz.system.station.mapper.StationMapper;
import com.hlytec.cloud.biz.system.station.model.entity.Station;
import com.hlytec.cloud.biz.system.user.utils.UserUtil;
import com.hlytec.cloud.common.constants.SysConstants;
import com.hlytec.cloud.common.exception.NetServiceException;
import com.hlytec.cloud.common.result.ResultEnum;
import com.hlytec.cloud.common.service.BaseService;

/**
 * @description: StationService
 * @author: JackChen
 * @date: 2021/5/21 14:24
 */
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class StationService extends BaseService<StationMapper, Station> {

    public List<Station> getStationTree(String area) {
        List<Station> result = new ArrayList<>();
        // 如果当前登录用户是admin,area可为空，查询所有站点数据
        String currentUserId = UserUtil.getCurrentUserId();
        if (StringUtils.isEmpty(area) && !StringUtils.equals(currentUserId, SysConstants.ADMIN_ID)) {
            throw new NetServiceException(ResultEnum.NO_DATA_ACCESS_AUTH);
        }
        List<Station> stations = findList(Station.builder().area(area).build());
        Map<String, List<Station>> collect = stations.parallelStream().collect(Collectors.groupingBy(Station::getArea));
        if (CollectionUtils.isEmpty(result)) {
            Station topVo = new Station();
            collect.forEach((key,list) -> {
                String prov = key.split("-")[0];
                String city = key.split("-")[1];
                String region = key.split("-")[2];
                if (StringUtils.isEmpty(topVo.getName())) {
                    buildFirstNode(result, topVo, list, prov, city, region);
                } else if (StringUtils.isNotEmpty(city)) {
                    List<String> provs = result.stream().map(Station::getName).collect(Collectors.toList());
                    for (Station node : result) {
                        if (StringUtils.equals(prov, node.getName())) {
                            List<String> citys = node.getChildNode().stream().map(Station::getName).collect(Collectors.toList());
                            if (!citys.contains(city)) {
                                Station leaf = Station.builder().name(city).build();
                                List<Station> trees3 = new ArrayList<>();
                                Station build = Station.builder().name(region).childNode(list).build();
                                trees3.add(build);
                                leaf.setChildNode(trees3);
                                node.getChildNode().add(leaf);
                            } else {
                                List<Station> childNode = node.getChildNode();
                                childNode.forEach(child -> {
                                    if (StringUtils.equals(child.getName(), city)) {
                                        List<String> regions = child.getChildNode().stream().map(Station::getName).collect(Collectors.toList());
                                        if (!regions.contains(region)) {
                                            Station leaf = Station.builder().name(region).childNode(list).build();
                                            child.getChildNode().add(leaf);
                                        }
                                    }
                                });
                            }
                            break;
                        }else if (!provs.contains(prov)) {
                            // 第一层
                            Station topVo2 = new Station();
                            buildFirstNode(result, topVo2, list, prov, city, region);
                            break;
                        }
                    }
                }
            });
        }
        return result;
    }

    private void buildFirstNode(List<Station> result, Station topVo, List<Station> list, String prov, String city,
        String region) {
        // 第一层
        topVo.setName(prov);
        List<Station> trees = new ArrayList<>();
        Station leaf = Station.builder().name(city).build();
        Station build = Station.builder().name(region).childNode(list).build();
        List<Station> trees2 = new ArrayList<>();
        trees2.add(build);
        leaf.setChildNode(trees2);
        trees.add(leaf);
        topVo.setChildNode(trees);
        result.add(topVo);
    }
}
