/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.rdb.sharding.jdbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import com.dangdang.ddframe.rdb.integrate.db.AbstractShardingDataBasesOnlyDBUnitTest;

public final class ShardingStatementTest extends AbstractShardingDataBasesOnlyDBUnitTest {
    
    private ShardingDataSource shardingDataSource;
    
    @Before
    public void init() throws SQLException {
        shardingDataSource = getShardingDataSource();
    }
    
    @Test
    public void assertExecuteQuery() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(sql)) {
            assertTrue(resultSet.next());
            assertThat(resultSet.getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteUpdate() throws SQLException {
        String sql = "DELETE FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertThat(stmt.executeUpdate(sql), is(40));
        }
    }
    
    @Test
    public void assertExecute() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertTrue(stmt.execute(sql));
            assertTrue(stmt.getResultSet().next());
            assertThat(stmt.getResultSet().getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteQueryWithResultSetTypeAndResultSetConcurrency() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = stmt.executeQuery(sql)) {
            assertTrue(resultSet.next());
            assertThat(resultSet.getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteQueryWithResultSetTypeAndResultSetConcurrencyAndResultSetHoldability() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ResultSet.HOLD_CURSORS_OVER_COMMIT);
                ResultSet resultSet = stmt.executeQuery(sql)) {
            assertTrue(resultSet.next());
            assertThat(resultSet.getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteQueryWithResultSetHoldabilityIsZero() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, 0);
                ResultSet resultSet = stmt.executeQuery(sql)) {
            assertTrue(resultSet.next());
            assertThat(resultSet.getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteUpdateWithAutoGeneratedKeys() throws SQLException {
        String sql = "DELETE FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertThat(stmt.executeUpdate(sql, Statement.NO_GENERATED_KEYS), is(40));
        }
    }
    
    @Test
    public void assertExecuteUpdateWithColumnIndexes() throws SQLException {
        String sql = "DELETE FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertThat(stmt.executeUpdate(sql, new int[] {1}), is(40));
        }
    }
    
    @Test
    public void assertExecuteUpdateWithColumnNames() throws SQLException {
        String sql = "DELETE FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertThat(stmt.executeUpdate(sql, new String[] {"orders_count"}), is(40));
        }
    }
    
    @Test
    public void assertExecuteWithAutoGeneratedKeys() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertTrue(stmt.execute(sql, Statement.NO_GENERATED_KEYS));
            assertTrue(stmt.getResultSet().next());
            assertThat(stmt.getResultSet().getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteWithColumnIndexes() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertTrue(stmt.execute(sql, new int[] {1}));
            assertTrue(stmt.getResultSet().next());
            assertThat(stmt.getResultSet().getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertExecuteWithColumnNames() throws SQLException {
        String sql = "SELECT COUNT(*) AS `orders_count` FROM `t_order` WHERE `status` = 'init'";
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertTrue(stmt.execute(sql, new String[] {"orders_count"}));
            assertTrue(stmt.getResultSet().next());
            assertThat(stmt.getResultSet().getLong(1), is(40L));
        }
    }
    
    @Test
    public void assertGetConnection() throws SQLException {
        try (
                Connection connection = shardingDataSource.getConnection();
                Statement stmt = connection.createStatement()) {
            assertThat(stmt.getConnection(), is(connection));
        }
    }
}
