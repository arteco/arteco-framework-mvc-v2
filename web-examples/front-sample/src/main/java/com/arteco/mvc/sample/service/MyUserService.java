package com.arteco.mvc.sample.service;

import com.arteco.mvc.sample.domain.MyUser;
import com.arteco.mvc.sample.domain.QMyUser;
import com.querydsl.sql.SQLQueryFactory;

import java.util.List;

/**
 * Created by rarnau on 2/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class MyUserService extends AbstractService {
    public List<MyUser> getUsers() {
        SQLQueryFactory queryFactory = super.getQueryFactory();
        QMyUser myUser = QMyUser.MyUser;
        return queryFactory.select(myUser).from(myUser).orderBy(myUser.username.asc()).fetch();
    }
}
