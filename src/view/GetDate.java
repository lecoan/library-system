package view;
/**
 * Created by zyx on 2017/4/1.
 */
public class GetDate {
    private static final int YEAR = 2000;
    private static final int MONTH = 1;
    private static final int DAY = 1;
    public GetDate () {}
    public static String getDate(int pastdays) {
        int year = YEAR, month = MONTH, day = DAY;
        while(pastdays > 0) {
            pastdays--;
            if(day < 28) day++;
            else {
                switch(month) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                        if(day < 31) day++;
                        else {
                            month++;
                            day = day%31 + 1;
                        }
                        break;
                    case 12:
                        if(day < 31) day++;
                        else {
                            month = 1;
                            day = 1;
                            year++;
                        }
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        if(day < 30) day++;
                        else {
                            month++;
                            day = 1;
                        }
                        break;
                    case 2:
                        if(year%4==0&&year%100!=0||year%400==0) {
                            if(day < 29) day++;
                            else {
                                month++;
                                day = 1;
                            }
                        }
                        else {
                            month++;
                            day = 1;
                        }
                        break;
                    default:
                }
            }
        }
        String date = new String();
        date = year + "-" + month + "-" + day;
        return date;
    }

}
