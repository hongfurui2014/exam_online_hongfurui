package com.itdupan.client;

import com.itdupan.api.RoleRightsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-user-service")
public interface RoleRightsClient extends RoleRightsApi {
}
