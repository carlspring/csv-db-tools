package org.carlspring.tools.csv.dao;

/**
 * Copyright 2012.
 *
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
 */

import org.carlspring.ioc.InjectionException;
import org.carlspring.ioc.PropertyValueInjector;
import org.carlspring.tools.csv.FieldUtils;
import org.carlspring.tools.csv.mapping.Configuration;
import org.carlspring.tools.csv.mapping.ConfigurationParser;
import org.carlspring.tools.csv.mapping.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

/**
 * @author mtodorov
 */
public class CSVDao
{

    private static final Logger logger = LoggerFactory.getLogger(CSVDao.class);

    private Configuration configuration;


    public CSVDao(String configurationXML)
    {
        try
        {
            PropertyValueInjector.inject(this);

            ConfigurationParser parser = new ConfigurationParser();
            parser.setConfigurationXML(configurationXML);

            configuration = parser.parse();
        }
        catch (InjectionException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void insert(List<String> fields,
                       List<String> row)
            throws SQLException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            final String sql = "INSERT INTO " + getTableName() + "(" + listFields(fields) + ") " +
                               "VALUES (" + generatePreparedStatementParameters(configuration.getMapping().getFields()) + ")";

            // if (logger.isDebugEnabled())
            //     logger.debug(sql);

            connection = getConnection();

            ps = connection.prepareStatement(sql);

            int i = 1;
            for (Field field : configuration.getMapping().getFields())
            {
                if (field.getIncludeExplicitly() == null)
                {
                    String value = null;
                    if (i - 1 < row.size())
                        value = row.get(i - 1);

                    setField(ps, i, field, value);

                    i++;
                }
                /*
                else
                {
                    if (field.getUseValue() != null && field.getUseValue().equalsIgnoreCase("SEQUENCE"))
                    {
                        ps.setS(i, getNextValueForSequence(configuration.getMapping().getSequenceLookupSQL()));
                    }
                }
                */
            }

            ps.executeUpdate();
        }
        finally
        {
            try
            {
                closeStatement(ps);
                closeConnection(connection);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public int export(List<Field> fields,
                      OutputStream os,
                      char delimiter,
                      String whereClause)
            throws SQLException, IOException
    {
        int numberOfRows = 0;

        Connection connection = null;
        Statement stmt = null;

        try
        {
            List<String> tableFields = FieldUtils.getTableFields(fields);

            final String sql = "SELECT " + listFields(tableFields) +
                               "  FROM " + getTableName() + (whereClause != null ?
                               " WHERE " + whereClause.trim() : "");

            if (logger.isDebugEnabled())
                logger.debug(sql);

            connection = getConnection();

            stmt = connection.createStatement();
            final ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
            {
                int i = 1;
                for (Field field : configuration.getMapping().getFields())
                {
                    if (field.getIncludeExplicitly() == null)
                    {
                        final String value = getField(rs, i, field);

                        os.write(value.getBytes());

                        if (i < configuration.getMapping().getFields().size())
                            os.write(delimiter);

                        i++;
                    }
                }

                os.write("\n".getBytes());
                os.flush();

                numberOfRows++;
            }

        }
        finally
        {
            try
            {
                closeStatement(stmt);
                closeConnection(connection);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return numberOfRows;
    }

    private void setField(PreparedStatement ps,
                          int i,
                          Field field,
                          String value)
            throws SQLException
    {
        // Handle primitives
        if (field.getType().equals("int"))
        {
            ps.setInt(i, Integer.parseInt(value));
        }
        else if (field.getType().equals("long"))
        {
            ps.setLong(i, Long.parseLong(value));
        }
        else if (field.getType().equals("float"))
        {
            ps.setFloat(i, Float.parseFloat(value));
        }
        else if (field.getType().equals("double"))
        {
            ps.setDouble(i, Double.parseDouble(value));
        }
        else if (field.getType().equals("boolean"))
        {
            ps.setBoolean(i, Boolean.parseBoolean(value));
        }
        // Handle objects
        else if (field.getType().equals("java.lang.String"))
        {
            ps.setString(i, value);
        }
        else if (field.getType().equals("java.sql.Date"))
        {
            ps.setDate(i, Date.valueOf(value));
        }
        else if (field.getType().equals("java.sql.Timestamp"))
        {
            ps.setTimestamp(i, Timestamp.valueOf(value));
        }
        else if (field.getType().equals("java.math.BigDecimal"))
        {
            ps.setBigDecimal(i, new BigDecimal(Long.parseLong(value)));
        }
    }

    private String getField(ResultSet resultSet,
                            int i,
                            Field field)
            throws SQLException
    {
        // Handle primitives
        if (field.getType().equals("int"))
        {
            return String.valueOf(resultSet.getInt(i));
        }
        else if (field.getType().equals("long"))
        {
            return String.valueOf(resultSet.getLong(i));
        }
        else if (field.getType().equals("float"))
        {
            return String.valueOf(resultSet.getFloat(i));
        }
        else if (field.getType().equals("double"))
        {
            return String.valueOf(resultSet.getDouble(i));
        }
        else if (field.getType().equals("boolean"))
        {
            return String.valueOf(resultSet.getBoolean(i));
        }
        // Handle objects
        else if (field.getType().equals("java.lang.String"))
        {
            return String.valueOf(resultSet.getString(i));
        }
        else if (field.getType().equals("java.sql.Date"))
        {
            return String.valueOf(resultSet.getDate(i));
        }
        else if (field.getType().equals("java.sql.Timestamp"))
        {
            return String.valueOf(resultSet.getTimestamp(i));
        }
        else if (field.getType().equals("java.math.BigDecimal"))
        {
            return String.valueOf(resultSet.getBigDecimal(i));
        }

        return null;
    }

    private String generatePreparedStatementParameters(List<Field> fields)
    {
        StringBuilder parameters = new StringBuilder();

        for (int i = 0; i < fields.size(); i++)
        {
            final Field field = fields.get(i);
            if (field.getUseValue() != null)
            {
                if (field.getUseValue().equalsIgnoreCase("DEFAULT"))
                {
                    parameters.append("DEFAULT");
                }
                else if (field.getUseValue().equalsIgnoreCase("SEQUENCE"))
                {
                    parameters.append(configuration.getMapping().getSequenceLookupSQL());
                }
            }
            else
            {
                parameters.append("?");
            }

            if (i < fields.size() - 1)
                parameters.append(", ");
        }

        return parameters.toString();
    }

    private String listFields(List<String> fields)
    {
        StringBuilder list = new StringBuilder();

        for (int i = 0; i < fields.size(); i++)
        {
            list.append(fields.get(i));

            if (i < fields.size() - 1)
                list.append(", ");
        }

        return list.toString();
    }

    public Connection getConnection()
            throws SQLException
    {
        Connection connection = null;

        try
        {
            Class.forName(getDriver());
            connection = DriverManager.getConnection(getJdbcURL(), getUsername(), getPassword());
        }
        catch (ClassNotFoundException e)
        {
            logger.error(e.getMessage());
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }

        return connection;
    }

    public long count()
            throws SQLException
    {
        return count(null);
    }

    public long count(String whereClause)
            throws SQLException
    {
        long count = 0;

        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;


        final String tableName = getTableName();
        final String sql = "SELECT COUNT(*)" +
                           "  FROM " + tableName + (whereClause != null ?
                                                    " WHERE " + whereClause : "");

        try
        {
            connection = getConnection();
            stmt = connection.createStatement();

            rs = stmt.executeQuery(sql);
            if (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            logger.error(e.getMessage());
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(connection);
        }

        return count;
    }

    public void deleteById(String fieldIdName,
                           long fieldIdValue)
            throws SQLException
    {
        Connection connection = null;
        PreparedStatement ps = null;

        try
        {
            final String sql = "DELETE FROM " + getTableName() +
                               " WHERE " + fieldIdName + " = ?";

            connection = getConnection();

            ps = connection.prepareStatement(sql);
            ps.setLong(1, fieldIdValue);
            ps.executeUpdate();
        }
        finally
        {
            try
            {
                closeStatement(ps);
                closeConnection(connection);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void deleteAll()
            throws SQLException
    {
        deleteByWhereClause(null);
    }

    public void deleteByWhereClause(String whereClause)
            throws SQLException
    {
        Connection connection = null;
        Statement stmt = null;

        try
        {
            final String sql = "DELETE FROM " + getTableName() + " " +
                               (whereClause != null ?
                                "WHERE " + whereClause : "");

            logger.debug(sql);

            connection = getConnection();

            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }
        finally
        {
            try
            {
                closeStatement(stmt);
                closeConnection(connection);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection(Connection connection)
            throws SQLException
    {
        if (connection != null)
        {
            connection.close();
        }
    }

    public void closeStatement(Statement statement)
            throws SQLException
    {
        if (statement != null)
        {
            statement.close();
        }
    }

    public void closeResultSet(ResultSet resultSet)
            throws SQLException
    {
        if (resultSet != null)
        {
            resultSet.close();
        }
    }

    public synchronized int getNextValueForSequence(String query)
            throws SQLException
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        int nextVal = -1;

        try
        {
            conn = getConnection();
            stmt = conn.createStatement();

            logger.info(query);

            rs = stmt.executeQuery(query);

            if (rs.next())
            {
                nextVal = rs.getInt(1);
            }
        }
        catch (SQLException e)
        {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            closeResultSet(rs);
            closeStatement(stmt);
            closeConnection(conn);
        }

        return nextVal;
    }

    public String getJdbcURL()
    {
        return configuration.getJdbcSettings().getUrl();
    }

    public String getDriver()
    {
        return configuration.getJdbcSettings().getDriver();
    }

    public String getUsername()
    {
        return configuration.getJdbcSettings().getUsername();
    }

    public String getPassword()
    {
        return configuration.getJdbcSettings().getPassword();
    }

    public String getTableName()
    {
        return configuration.getMapping().getTableName();
    }

    public Configuration getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(Configuration configuration)
    {
        this.configuration = configuration;
    }

}
