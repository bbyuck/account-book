package com.bb.accountbook.common.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;

public class DateTimeUtil {
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String PARAMETER_FORMAT = "yyyyMM";

    public static LocalDate[] getMonthlyDuration(String yearMonth) {
        LocalDate[] answer = new LocalDate[2];

        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(4, 6));

        answer[0] = getMonthlyStartDate(year, month);
        answer[1] = getMonthlyEndDate(year, month);

        return answer;
    }

    public static LocalDate[] getThreeMonthUnitDuration(String yearMonth) {
        LocalDate[] answer = new LocalDate[2];

        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(4, 6));

        answer[0] = getMonthlyStartDate(month == 1 ? year - 1 : year, month == 1 ? 12 : month);
        answer[1] = getMonthlyEndDate(month == 12 ? year + 1 : year, month == 12 ? 1 : month);

        return answer;
    }

    public static LocalDate getMonthlyStartDate(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate getMonthlyEndDate(int year, int month) {
        Set<Integer> monthWith31Days = Set.of(1, 3, 5, 7, 8, 10, 12);

        if (monthWith31Days.contains(month)) {
            return LocalDate.of(year, month, 31);
        }
        else if (month == 2) {
            if (isLeapYear(year)) {
                return LocalDate.of(year, month, 29);
            }
            else {
                return LocalDate.of(year, month, 28);
            }
        }
        else {
            return LocalDate.of(year, month,30);
        }
    }

    public static boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            }
            return true;
        }
        return false;
    }

    public static boolean isOneYearOrMoreApart(LocalDate date1, LocalDate date2) {
        // date1이 더 이후의 날짜로 설정하기 위해 순서를 정리.
        LocalDate startDate = (date1.isBefore(date2)) ? date1 : date2;
        LocalDate endDate = (date1.isBefore(date2)) ? date2 : date1;

        // Period를 사용하여 두 날짜 사이의 차이를 계산.
        Period period = Period.between(startDate, endDate);

        // 두 날짜 사이의 차이가 1년 이상인지 체크.
        return period.getYears() >= 1;
    }
}
