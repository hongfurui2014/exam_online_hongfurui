package com.itdupan.feign;

import com.itdupan.api.UserQApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-user-service")
public interface UserQClient extends UserQApi {
}
