package com.whotel.common.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.whotel.common.entity.Region;
import com.whotel.common.service.RegionService;
import com.whotel.common.util.SpringContextHolder;

public class SysCache {
	
	private static final Logger logger = Logger.getLogger(SysCache.class);

	private static Map<Integer, List<Region>> regionMap = Collections.synchronizedMap(new HashMap<Integer, List<Region>>());
	
	private static List<Region> provinces = Collections.synchronizedList(new ArrayList<Region>());
	
	private SysCache() {}
	
	private static SysCache sysCache = new SysCache();

	public static SysCache getInstance() {
		return sysCache == null ? new SysCache() : sysCache;
	}
	/**
	 * 缓存初始化
	 */
	public synchronized void initialize() {
		logger.info(">>>>>> Starting system initialization......");
		long start = System.currentTimeMillis();
		loadRegions();
		long end = System.currentTimeMillis();
		logger.info(">>>>>> Initialization has been completed, total take " + (end - start)/1000.0 + " seconds.");
	}
	
	public static void loadRegions() {
		provinces.clear();
		regionMap.clear();
		RegionService regionService = SpringContextHolder.getBean(RegionService.class);
		List<Region> regions = regionService.findAllRegions();
		
		if(regions != null) {
			for(Region region:regions) {
				if(region.getParent() == null) {
					provinces.add(region);
				} else {
					Integer parentId = region.getParent().getRegionId();
					List<Region> childRegions = regionMap.get(parentId);
					if(childRegions == null) {
						childRegions = new ArrayList<Region>();
						regionMap.put(parentId, childRegions);
					}
					childRegions.add(region);
				}
			}
		}
	}
	
	public static List<Region> getProvinces() {
		return provinces;
	}
	
	public static List<Region> getSubRegion(Integer id) {
		return regionMap.get(id);
	}
}
