package group5.sipenmaru.model;

import java.util.List;

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
        private String virtualAccountNumber;
        private List<String> instructions;
    }
} 