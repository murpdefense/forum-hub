package br.com.soupaulodev.forumhub.modules.user.entity;

/**
 * Forums the user is participating in.
 */
public enum UserRole {
    /**
     * Role for administrative users with full access.
     */
    ADMIN,

    /**
     * Role for users who can moderate content.
     */
    MODERATOR,

    /**
     * Role for regular users with standard access.
     */
    USER
}
