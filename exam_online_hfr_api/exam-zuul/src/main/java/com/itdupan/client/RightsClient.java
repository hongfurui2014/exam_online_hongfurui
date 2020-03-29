package com.itdupan.client;

import com.itdupan.api.RightsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-user-service")
public interface RightsClient extends RightsApi {
}
