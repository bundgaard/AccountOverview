package club.lonelypenguin.oscar.models;

import java.math.BigDecimal;

/**
 * Created by dbundgaard on 2016-04-15.
 */
public class AccountSummaryMonth {

    private String _date;
    private String _action;
    private String _category;
    private BigDecimal _amount;

    public AccountSummaryMonth(String _date, String _action, String _category, BigDecimal _amount) {
        this._date = _date;
        this._action = _action;
        this._category = _category;
        this._amount = _amount;
    }

    public AccountSummaryMonth() {

    }

    @Override
    public String toString() {
        return "AccountSummaryMonth{" +
                "_date=" + _date +
                ", _action='" + _action + '\'' +
                ", _category='" + _category + '\'' +
                ", _amount=" + _amount +
                '}';
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_action() {
        return _action;
    }

    public void set_action(String _action) {
        this._action = _action;
    }

    public String get_category() {
        return _category;
    }

    public void set_category(String _category) {
        this._category = _category;
    }

    public BigDecimal get_amount() {
        return _amount;
    }

    public void set_amount(BigDecimal _amount) {
        this._amount = _amount;
    }
}
