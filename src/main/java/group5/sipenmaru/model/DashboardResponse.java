package group5.sipenmaru.model;

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
public class DashboardResponse {
    @JsonProperty("total_applicants")
    private Integer totalApplicants;

    @JsonProperty("total_applicants_this_month")
    private Integer totalApplicantsThisMonth;

    @JsonProperty("recent_applicants")
    private List<RecentApplicant> recentApplicants;

    @JsonProperty("gender_stats")
    private GenderStats genderStats;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RecentApplicant {
        @JsonProperty("full_name")
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