package com.example.YuRun.Admin.Homepage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyActivity {
    private String month;
    private int activityCount;
}