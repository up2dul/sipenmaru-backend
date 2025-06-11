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
public class DashboardResponse {
    private Integer totalApplicants;
    private Integer totalApplicantsThisMonth;
    private List<RecentApplicant> recentApplicants;
    private GenderStats genderStats;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RecentApplicant {
        private String fullName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GenderStats {
        private Integer male;
        private Integer female;
    }
} 