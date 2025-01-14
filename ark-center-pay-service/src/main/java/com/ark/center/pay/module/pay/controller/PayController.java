package com.ark.center.pay.module.pay.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ark.center.pay.api.PayApi;
import com.ark.center.pay.api.dto.request.PayOrderCreateCmd;
import com.ark.center.pay.api.dto.request.PayOrderPageQueryReqDTO;
import com.ark.center.pay.api.dto.response.PayOrderCreateDTO;
import com.ark.center.pay.module.pay.service.PayOrderService;
import com.ark.component.dto.PageResponse;
import com.ark.component.dto.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import com.ark.component.web.base.BaseController;

import java.util.Map;

/**
 * <p>
 * 支付订单表 前端控制器
 * </p>
 *
 * @author EOP
 * @since 2022-08-11
 */
@Schema(name = "支付管理", description = "支付管理")
@Validated
@RestController
@RequestMapping("/v1/pay")
@RequiredArgsConstructor
public class PayController extends BaseController implements PayApi {

    private final PayOrderService payOrderService;

    @Operation(summary = "")
    @PostMapping("/order/create")
    public SingleResponse<PayOrderCreateDTO> createPayOrder(@RequestBody @Validated PayOrderCreateCmd reqDTO) {
        return SingleResponse.ok(payOrderService.createPayOrder(reqDTO));
    }

    @Operation(summary = "获取支付单状态")
    @GetMapping("/order/status")
    public SingleResponse<Integer> getPayOrderStatus(@RequestParam Long id) {
        return SingleResponse.ok(payOrderService.getPayOrderStatus(id));
    }

    @Operation(summary = "订单通知")
    @PostMapping("/notify")
    public SingleResponse<Map<String, Object>> notify(@RequestBody JSONObject reqDTO) {
        Map<String, Object> notify = payOrderService.notify(reqDTO);
        return SingleResponse.ok(notify);
    }

    @Operation(summary = "查询支付订单表分页列表")
    @PostMapping("/page")
    public SingleResponse<PageResponse<PayOrderCreateDTO>> pageList(@RequestBody @Validated PayOrderPageQueryReqDTO queryDTO) {
        return SingleResponse.ok(payOrderService.getPageList(queryDTO));
    }

    @Operation(summary = "查询支付订单表详情",
            parameters = {
                    @Parameter(name = "id", description = "支付单id")
            }
    )
    @GetMapping("/info")
    public SingleResponse<PayOrderCreateDTO> info(@RequestParam(required = false)
                                                      @NotNull(message = "id不能为空") Long id) {
        return SingleResponse.ok(payOrderService.getPayOrderInfo(id));
    }


}
