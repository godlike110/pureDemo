package com.waimai.ops.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("")
public class ApiController {

  // @ResponseBody
  @RequestMapping(value = "v1/app/test", method = RequestMethod.GET)
  public ModelAndView testPage() {
    return new ModelAndView("api/test");
  }
  
  @RequestMapping(value="v1/app/err" , method = RequestMethod.GET)
  public ModelAndView errPage(@RequestParam("str") String str) {
	  Map<String, Object> map = new HashMap<String, Object>();
	  str = str.concat("sa");
	  map.put("str", str);
	  return new ModelAndView("index", map);
  }

  @RequestMapping(value = "version", method = RequestMethod.GET)
  public @ResponseBody String check_Test(@RequestParam("platform") String platform,
      @RequestParam("os") String os) throws InterruptedException, ExecutionException {
    String version = "1.0";
    TreeMap<String, String> treeMap = new TreeMap<String, String>();
    treeMap.put("version", version);
    String sign = "fefasfe";
    System.out.println(sign);
    String result;
    result = "";
    return result;
  }
}
