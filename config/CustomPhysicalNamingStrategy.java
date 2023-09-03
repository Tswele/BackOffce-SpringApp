package za.co.wirecard.channel.backoffice.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CustomPhysicalNamingStrategy implements PhysicalNamingStrategy {

    private static final Logger logger = LogManager.getLogger(CustomPhysicalNamingStrategy.class);
    private final List<String> unmappedColumns = new ArrayList<>(Arrays.asList("Logger", "Priority", "Loc_ThreadName", "Loc_ClassName", "Loc_MethodName", "Loc_FileName", "Loc_LineNumber", "TransactionID", "Msg", "Request", "Throwable", "timestamp"));

    @Override
    public Identifier toPhysicalCatalogName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        // return convertToSnakeCase(identifier);
        return this.apply(identifier, jdbcEnv);
    }

    @Override
    public Identifier toPhysicalColumnName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        return convertToSnakeCase(identifier, jdbcEnv);
    }

    @Override
    public Identifier toPhysicalSchemaName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        // return convertToSnakeCase(identifier);
        return this.apply(identifier, jdbcEnv);
    }

    @Override
    public Identifier toPhysicalSequenceName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        // return convertToSnakeCase(identifier);
        return this.apply(identifier, jdbcEnv);
    }

    @Override
    public Identifier toPhysicalTableName(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        //return convertToSnakeCase(identifier);3
        return this.apply(identifier, jdbcEnv);
    }

    private Identifier convertToSnakeCase(final Identifier identifier, final JdbcEnvironment jdbcEnv) {
        if (identifier != null) {
            if (unmappedColumns.stream().anyMatch(s -> s.equalsIgnoreCase(identifier.getText()))) {
                logger.info(identifier.getText());
                return Identifier.toIdentifier(identifier.getText());
            }
            return this.apply(identifier, jdbcEnv);
        }
        return Identifier.toIdentifier("");
    }

    private Identifier apply(Identifier name, JdbcEnvironment jdbcEnvironment) {
        if (name == null) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder(name.getText().replace('.', '_'));

            for(int i = 1; i < builder.length() - 1; ++i) {
                if (this.isUnderscoreRequired(builder.charAt(i - 1), builder.charAt(i), builder.charAt(i + 1))) {
                    builder.insert(i++, '_');
                }
            }

            return this.getIdentifier(builder.toString(), name.isQuoted(), jdbcEnvironment);
        }
    }

    protected Identifier getIdentifier(String name, boolean quoted, JdbcEnvironment jdbcEnvironment) {
        if (this.isCaseInsensitive(jdbcEnvironment)) {
            name = name.toLowerCase(Locale.ROOT);
        }

        return new Identifier(name, quoted);
    }

    protected boolean isCaseInsensitive(JdbcEnvironment jdbcEnvironment) {
        return true;
    }

    private boolean isUnderscoreRequired(char before, char current, char after) {
        return Character.isLowerCase(before) && Character.isUpperCase(current) && Character.isLowerCase(after);
    }

}
