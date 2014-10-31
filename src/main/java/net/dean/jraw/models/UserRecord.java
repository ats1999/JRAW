package net.dean.jraw.models;

import org.codehaus.jackson.JsonNode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents a small bit of data relevant to moderation logs such as banned users, accepted contributors,
 * and moderators.
 */
public class UserRecord extends Thing {

    /**
     * Instantiates a new UserRecord
     *
     * @param dataNode The node to parse data from
     */
    public UserRecord(JsonNode dataNode) {
        super(dataNode);
    }

    /**
     * This property has multiple meanings depending on the context. If this record is referring to a user being banned
     * from or being added as a contributor to either the subreddit or the wiki, then this will return the date they
     * were banned or added as a contributor. If this record is referring to a contributor or moderator, then this will
     * return the date that the account was created.
     * @return The date most relevant to this record
     */
    @JsonProperty
    public Date getDate() {
        return data("date", Date.class);
    }

    /**
     * Gets a list of mod permissions. Only applicable if this record is referring to a moderator.
     * @return A list that this moderator has on this subreddit
     */
    @JsonProperty(nullable = true)
    public List<ModPermission> getModPermissions() {
        if (!data.has("mod_permissions")) {
            return null;
        }
        JsonNode permsNode = data.get("mod_permissions");
        if (!permsNode.isArray()) {
            return null;
        }

        List<ModPermission> perms = new ArrayList<>(permsNode.size());
        for (JsonNode perm : permsNode) {
            perms.add(ModPermission.valueOf(perm.asText().toUpperCase()));
        }
        return perms;
    }

    /**
     * Gets the reason why this user was banned. This is not visible to the user.
     * @return The explanation for the banning
     */
    @JsonProperty(nullable = true)
    public String getNote() {
        return data("note");
    }

    @Override
    public ThingType getType() {
        // This class has no explicit type (at least as specified by a JSON response), but it needs (at a minimum) to
        // extend Thing so it can be a valid generic parameter in Paginator
        return null;
    }

    /**
     * All possible moderator permissions. The
     * <a href="https://www.reddit.com/r/modhelp/wiki/mod_permissions">/r/modhelp wiki page on mod permissions</a> has
     * heavily influenced the documentation in this class.
     */
    public static enum ModPermission {
        /** Access to all moderator resources */
        ALL,
        /** Manage the lists of approved submitters and banned users */
        ACCESS,
        /** Edit settings, sidebar, css, and images */
        CONFIG,
        /** Manage user flair, link flair, and flair templates. */
        FLAIR,
        /** Read and reply to moderator mail */
        MAIL,
        /** Use the approve, remove, spam, distinguish, and nsfw buttons */
        POSTS,
        /** View the moderation logs and traffic stats */
        WIKI
    }
}
