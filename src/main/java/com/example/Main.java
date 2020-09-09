/*
 * Copyright 2002-2014 the original author or authors.
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

package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Map;

import canvas.SignedRequest;

@Controller
@SpringBootApplication
public class Main {
  private static final String yourConsumerSecret = "795D7EF172CF81210E011FC1FC3DCF84336CB00C7B1D6F033733D4686AC690ED";

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @RequestMapping("/")
  String index() {
    return "index";
  }

  @RequestMapping("/testing")
  String testing(WebRequest request, Map<String, Object> model) {
    model.put("cool", "sweet");

    // Pull the signed request out of the request body and verify and decode it.
    String signedRequest = request.getParameter("signed_request");

    if (signedRequest == null) {
        model.put("message", "This app must be invoked via a signed request!");
        return "error";
    }

    String signedRequestJson = SignedRequest.verifyAndDecodeAsJson(signedRequest, yourConsumerSecret);

    model.put("signed_request", signedRequestJson);

    return "testing";
  }

  @RequestMapping("/usermanagement")
  String usermanagement() {
    return "usermanagement";
  }

  @RequestMapping("/stp")
  String stp() {
    return "stp";
  }
}
