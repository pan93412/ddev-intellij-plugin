package de.php_perfect.intellij.ddev.cmd;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Description {

    public enum Status {
        @SerializedName("running") RUNNING,
        @SerializedName("starting") STARTING,
        @SerializedName("stopped") STOPPED,
        @SerializedName("project directory missing") DIR_MISSING,
        @SerializedName(".ddev/config.yaml missing") CONFIG_MISSING,
        @SerializedName("paused") PAUSED,
        @SerializedName("unhealthy") UNHEALTHY,
    }

    private final @Nullable String name;

    private final @Nullable String phpVersion;

    private final @Nullable Status status;

    @SerializedName("mailhog_https_url")
    private final @Nullable String mailHogHttpsUrl;

    @SerializedName("mailhog_url")
    private final @Nullable String mailHogHttpUrl;

    private final @Nullable Map<String, Service> services;

    @SerializedName("dbinfo")
    private final @Nullable DatabaseInfo databaseInfo;

    @SerializedName("primary_url")
    private final @Nullable String primaryUrl;

    public Description(@Nullable String name, @Nullable String phpVersion, @Nullable Status status,
                       @Nullable String mailHogHttpsUrl, @Nullable String mailHogHttpUrl, @Nullable DatabaseInfo databaseInfo, @Nullable String primaryUrl) {
        this(name, phpVersion, status, mailHogHttpsUrl, mailHogHttpUrl, new HashMap<>(), databaseInfo, primaryUrl);
    }

    public Description(@Nullable String name, @Nullable String phpVersion, @Nullable Status status,
                       @Nullable String mailHogHttpsUrl, @Nullable String mailHogHttpUrl, @Nullable Map<String, Service> services,
                       @Nullable DatabaseInfo databaseInfo, @Nullable String primaryUrl) {
        this.name = name;
        this.phpVersion = phpVersion;
        this.status = status;
        this.mailHogHttpsUrl = mailHogHttpsUrl;
        this.mailHogHttpUrl = mailHogHttpUrl;
        this.services = services;
        this.databaseInfo = databaseInfo;
        this.primaryUrl = primaryUrl;
    }

    public @Nullable String getName() {
        return name;
    }

    public @Nullable String getPhpVersion() {
        return this.phpVersion;
    }

    public @Nullable Status getStatus() {
        return this.status;
    }

    public @Nullable String getMailHogHttpsUrl() {
        return this.mailHogHttpsUrl;
    }

    public @Nullable String getMailHogHttpUrl() {
        return this.mailHogHttpUrl;
    }

    public @NotNull Map<String, Service> getServices() {
        if (this.services == null) {
            return new HashMap<>();
        }

        var serviceMap = new HashMap<>(this.services);

        if (this.getMailHogHttpsUrl() != null || this.getMailHogHttpUrl() != null) {
            serviceMap.put("mailhog", new Service("ddev-config-test-mailhog", this.getMailHogHttpsUrl(), this.getMailHogHttpUrl()));
        }

        return serviceMap;
    }

    public @Nullable DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    public @Nullable String getPrimaryUrl() {
        return primaryUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Description that = (Description) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getPhpVersion(), that.getPhpVersion())
                && getStatus() == that.getStatus() && Objects.equals(getMailHogHttpsUrl(), that.getMailHogHttpsUrl())
                && Objects.equals(getMailHogHttpUrl(), that.getMailHogHttpUrl()) && Objects.equals(getServices(),
                that.getServices()) && Objects.equals(getDatabaseInfo(), that.getDatabaseInfo())
                && Objects.equals(getPrimaryUrl(), that.getPrimaryUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPhpVersion(), getStatus(), getMailHogHttpsUrl(), getMailHogHttpUrl(),
                getServices(), getDatabaseInfo(), getPrimaryUrl());
    }

    @Override
    public String toString() {
        return "Description{" +
                "name='" + name + '\'' +
                ", phpVersion='" + phpVersion + '\'' +
                ", status=" + status +
                ", mailHogHttpsUrl='" + mailHogHttpsUrl + '\'' +
                ", mailHogHttpUrl='" + mailHogHttpUrl + '\'' +
                ", services=" + services +
                ", databaseInfo=" + databaseInfo +
                ", primaryUrl=" + primaryUrl +
                '}';
    }
}
