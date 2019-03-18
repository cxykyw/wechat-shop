package com.kyw.fegin;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.kyw.api.server.UserService;

@FeignClient("member")
public interface UserFeign extends UserService{

}
