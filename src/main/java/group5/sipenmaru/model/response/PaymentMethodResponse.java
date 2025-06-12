package group5.sipenmaru.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentMethodResponse {
    private Double amount;

    private List<PaymentMethod> methods;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PaymentMethod {
        private String name;

        @JsonProperty("virtual_account_number")
        private String virtualAccountNumber;

        private List<String> instructions;
    }
} 