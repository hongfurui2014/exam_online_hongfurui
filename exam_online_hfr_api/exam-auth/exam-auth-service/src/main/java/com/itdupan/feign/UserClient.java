package com.itdupan.feign;

import com.itdupan.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-user-service")
public interface UserClient extends UserApi {


}
