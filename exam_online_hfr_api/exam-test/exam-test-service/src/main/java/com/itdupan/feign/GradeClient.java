package com.itdupan.feign;

import com.itdupan.api.GradeApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-school-service")
public interface GradeClient extends GradeApi {
}
