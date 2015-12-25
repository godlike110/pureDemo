package com.waimai.ops.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ShellService {

	public static Logger logger = LoggerFactory.getLogger(ShellService.class);

	/**
	 * 执行shell脚本
	 * 
	 * @param shell
	 * @return
	 */
	public List<String> execuShell(String shell) {
		Process process = null;
		List<String> processList = new ArrayList<String>();
		try {
			process = Runtime.getRuntime().exec(shell);
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = input.readLine()) != null) {
				processList.add(line);
			}
			input.close();
		} catch (IOException e) {
			logger.error("", e);
		}
		return processList;
	}

}
