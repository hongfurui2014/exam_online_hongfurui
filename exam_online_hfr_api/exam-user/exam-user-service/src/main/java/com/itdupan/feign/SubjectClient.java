package com.itdupan.feign;

import com.itdupan.api.SubjectApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("exam-school-service")
public interface SubjectClient extends SubjectApi {
}
