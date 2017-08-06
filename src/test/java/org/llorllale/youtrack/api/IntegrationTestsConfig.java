/*
 * Copyright 2017 George Aristy george.aristy AT gmail DOT com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.llorllale.youtrack.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 *
 * @author George Aristy george.aristy AT gmail DOT com
 */
public class IntegrationTestsConfig {
  private static final String CONFIG_FILE = "/integration-tests-config.properties";
  private static final Properties CONFIG = new Properties();

  public synchronized String youtrackUser(){
    if(CONFIG.isEmpty()){
      loadConfig();
    }

    return CONFIG.getProperty("youtrack.test.user");
  }

  public char[] youtrackPwd(){
    if(CONFIG.isEmpty()){
      loadConfig();
    }

    return CONFIG.getProperty("youtrack.test.pwd").toCharArray();
  }

  public URL youtrackURL(){
    if(CONFIG.isEmpty()){
      loadConfig();
    }

    try{
      return new URL(CONFIG.getProperty("youtrack.test.url"));
    }catch(MalformedURLException e){
      throw new RuntimeException("Malformed URL: " + CONFIG.getProperty("youtrack.test.url"));
    }
  }

  private void loadConfig() {
    try(InputStream input = IntegrationTestsConfig.class.getResourceAsStream(CONFIG_FILE)){
      CONFIG.load(input);
    }catch(IOException e){
      throw new RuntimeException("Missing integration-tests configuration!", e);
    }
  }
}