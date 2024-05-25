package com.pde.config;

import org.apache.logging.log4j.util.Strings;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * @author abbas
 */
public class PDEClientDatabaseNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {
    /**
     * Let's dive deeper into Hibernate. According to the documentation, there are two interfaces responsible for naming
     * your tables, columns etc. in Hibernate: ImplicitNamingStrategy and PhysicalNamingStrategy.
     * <p>
     * ImplicitNamingStrategy is in charge of naming all objects that were not explicitly named by a developer:
     * e.g. entity name, table name, column name, index, FK etc. The resulting name is called the logical name,
     * it is used internally by Hibernate to identify an object. It is not the name that gets put into the DB.
     * <p>
     * PhysicalNamingStrategy provides the actual physical name used in the DB based on the logical JPA object name.
     * Effectively, this means that using Hibernate you cannot specify database object names directly, but only
     * logical ones. To have a better understanding of what's happening under the hood, see the diagram below.
     */
    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        String columnName = logicalName.getText();
        columnName = columnName.replace(columnName.charAt(0) ,Character.toLowerCase(columnName.toCharArray()[0]));
        StringBuilder columnNameWithUnderscore = new StringBuilder();
        char[] characters = columnName.toCharArray();

        for (char character : characters) {
            if (Character.isUpperCase(character))
                columnNameWithUnderscore.append('_');
            columnNameWithUnderscore.append(Character.toUpperCase(character));
        }
        final String t = "T_" + columnNameWithUnderscore;
        return new Identifier(t, logicalName.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        final String columnName = logicalName.getText();
        if (columnName.contains("FK")) {
            return new Identifier(columnName, logicalName.isQuoted());
        }

        StringBuilder columnNameWithUnderscore = new StringBuilder();
        char[] characters = columnName.toCharArray();

        for (char character : characters) {
            if (Character.isUpperCase(character))
                columnNameWithUnderscore.append('_');
            columnNameWithUnderscore.append(Character.toUpperCase(character));
        }

        final String t = "C_" + columnNameWithUnderscore;
        return new Identifier(t, logicalName.isQuoted());
    }

}
