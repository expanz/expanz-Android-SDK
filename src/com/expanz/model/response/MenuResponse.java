package com.expanz.model.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Encapsulates menu response data
 * 
 * Forms the Cammond output part of the {@link ExpanzCommand} object
 * 
 */
public class MenuResponse {

	/**
	 * The Process Areas 
	 */
	private Map<String, ProcessAreaResponse> processAreas = new HashMap<String, ProcessAreaResponse>();

	private List<RoleResponse> roles = new ArrayList<RoleResponse>();

	public ProcessAreaResponse getProcessAreaById(String id) {
		return processAreas.get(id);
	}

	public void addProcessArea(ProcessAreaResponse processArea) {
		processAreas.put(processArea.getId(), processArea);
	}

	public List<RoleResponse> getRoles() {
		return roles;
	}

	public Map<String, ProcessAreaResponse> getProcessAreas() {
		return processAreas;
	}

}
