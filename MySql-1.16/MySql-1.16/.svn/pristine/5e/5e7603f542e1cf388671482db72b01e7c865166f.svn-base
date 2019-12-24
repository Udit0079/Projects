package com.hrms.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author ahada
 * 
 */
public class ActivityDetailsComparator {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ActivityDetailsComparator.class);

	/**
	 * @param updatedActivityDetailData
	 * @param originalActivityDetailData
	 * @return
	 */
	public static boolean isActivityDueDateChanged(Map<String, String> updatedActivityDetailData,
			Map<String, String> originalActivityDetailData) {
		
		String activityOriginalDueDate = originalActivityDetailData.get("dueBy");
		String activityUpdatedDueDate = updatedActivityDetailData.get("dueBy");
		if (!activityOriginalDueDate.equals(activityUpdatedDueDate)) {
			return true;
		}
		return false;
	}

	/**
	 * @param updatedActivityDetailData
	 * @param originalActivityDetailData
	 * @return
	 */
	public static boolean isActivityStatusChanged(Map<String, String> updatedActivityDetailData,
			Map<String, String> originalActivityDetailData) {
		String activityOriginalStatus = originalActivityDetailData.get("status");
		String activityUpdatedStatus = updatedActivityDetailData.get("status");
		if (!activityOriginalStatus.equals(activityUpdatedStatus)) {
			return true;
		}
		return false;
	}

	/**
	 * @param updatedActivityDetailData
	 * @param originalActivityDetailData
	 * @return
	 */
	public static boolean isActivityOwnersChanged(Map<String, String> updatedActivityDetailData,
			Map<String, String> originalActivityDetailData) {
		String originalActivityOwnerNames = originalActivityDetailData.get("activityOwners");
		String updatedActivityOwnersNames = updatedActivityDetailData.get("activityOwnerNames");
		// String originalActivityOwnersIDs = originalActivityDetailData.get("userIds");
		// String updatedActivityOwnersIDs = updatedActivityDetailData.get("activityOwners");

		if (!originalActivityOwnerNames.equals(updatedActivityOwnersNames)) {
			return true;
		}
		return false;
	}

	/**
	 * @param updatedActivityDetailData
	 * @param originalActivityDetailData
	 * @return
	 */
	public static Map<String, Map<String, String>> getAddedAndRemmovedActivityOwners(
			Map<String, String> updatedActivityDetailData, Map<String, String> originalActivityDetailData) {
		
		long begin = System.nanoTime();
		String originalActivityOwnerNames = originalActivityDetailData.get("activityOwners");
		String originalActivityOwnerID = originalActivityDetailData.get("activityOwnerIDs");
		String updatedActivityOwnersNames = updatedActivityDetailData.get("activityOwnerNames");
		String updatedActivityOwnersID = updatedActivityDetailData.get("activityOwnerIDs");

		String[] originalActivityOwnersNamesArray = originalActivityOwnerNames.split(",");
		String[] originalActivityOwnersIDArray = originalActivityOwnerID.split(",");
		String[] updatedActivityOwnersNamesArray = updatedActivityOwnersNames.split(",");
		String[] updatedActivityOwnersIDArray = updatedActivityOwnersID.split(",");

		Map<String, String> originalActivityOwnerNamesMap = new HashMap<String, String>();
		Map<String, String> originalActivityOwnerIDMap = new HashMap<String, String>();
		Map<String, String> updatedActivityOwnerNamesMap = new HashMap<String, String>();
		Map<String, String> updatedActivityOwnerIDMap = new HashMap<String, String>();
		Map<String, Map<String, String>> addedRemovedActivityOwnersMap = new HashMap<String, Map<String, String>>();

		for (String activityOwnerName : originalActivityOwnersNamesArray) {
			originalActivityOwnerNamesMap.put(activityOwnerName.trim(), activityOwnerName.trim());
		}
		for (String activityOwnerID : originalActivityOwnersIDArray) {
			originalActivityOwnerIDMap.put(activityOwnerID.trim(), activityOwnerID.trim());
		}

		for (String activityOwnerName : updatedActivityOwnersNamesArray) {
			updatedActivityOwnerNamesMap.put(activityOwnerName.trim(), activityOwnerName.trim());
		}
		for (String activityOwnerID : updatedActivityOwnersIDArray) {
			updatedActivityOwnerIDMap.put(activityOwnerID.trim(), activityOwnerID.trim());
		}

		if ((originalActivityOwnerNamesMap.size() == 0) && (updatedActivityOwnerNamesMap.size() > 0)) {
			addedRemovedActivityOwnersMap.put("ADDED", updatedActivityOwnerNamesMap);
			addedRemovedActivityOwnersMap.put("ADDED_ID", updatedActivityOwnerIDMap);
			return addedRemovedActivityOwnersMap;
		} else if ((originalActivityOwnerNamesMap.size() > 0) && (updatedActivityOwnerNamesMap.size() == 0)) {
			addedRemovedActivityOwnersMap.put("REMOVED", originalActivityOwnerNamesMap);
			addedRemovedActivityOwnersMap.put("REMOVED_ID", originalActivityOwnerIDMap);
			return addedRemovedActivityOwnersMap;
		} else {
			Map<String, String> originalActivityOwnerNamesMapClone = new HashMap<String, String>(
				originalActivityOwnerNamesMap);
			if (originalActivityOwnerNamesMapClone.size() > 0) {
				Iterator iterator = originalActivityOwnerNamesMapClone.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					if (updatedActivityOwnerNamesMap.get(key) != null) {
						originalActivityOwnerNamesMap.remove(key);
						updatedActivityOwnerNamesMap.remove(key);
					}
				}
			}
			Map<String, String> originalActivityOwnerIDMapClone = new HashMap<String, String>(
				originalActivityOwnerIDMap);
			if (originalActivityOwnerIDMapClone.size() > 0) {
				Iterator iterator = originalActivityOwnerIDMapClone.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					if (updatedActivityOwnerIDMap.get(key) != null) {
						originalActivityOwnerIDMap.remove(key);
						updatedActivityOwnerIDMap.remove(key);
					}
				}
			}
			if (originalActivityOwnerNamesMap.size() > 0) {
				addedRemovedActivityOwnersMap.put("REMOVED", originalActivityOwnerNamesMap);
			}
			if (originalActivityOwnerIDMap.size() > 0) {
				addedRemovedActivityOwnersMap.put("REMOVED_ID", originalActivityOwnerIDMap);
			}
			if (updatedActivityOwnerNamesMap.size() > 0) {
				addedRemovedActivityOwnersMap.put("ADDED", updatedActivityOwnerNamesMap);
			}
			if (updatedActivityOwnerIDMap.size() > 0) {
				addedRemovedActivityOwnersMap.put("ADDED_ID", updatedActivityOwnerIDMap);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Execution time for getAddedAndRemmovedActivityOwners is "
						+ (System.nanoTime() - begin) * 0.000000001 + " seconds");
			}
			return addedRemovedActivityOwnersMap;
		}

	}
}
