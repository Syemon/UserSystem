package com.syemon.usersystem.dataaccess;

record GithubUser (
    String login,
    Long id,
    String node_id,
    String avatar_url,
    String gravatar_id,
    String url,
    String html_url,
    String followers_url,
    String following_url,
    String gists_url,
    String starred_url,
    String subscriptions_url,
    String organizations_url,
    String repos_url,
    String events_url,
    String received_events_url,
    String type,
    Boolean site_admin,
    String name,
    String company,
    String blog,
    String location,
    String email,
    Boolean hireable,
    String bio,
    String twitter_username,
    Long public_repos,
    Long public_gists,
    Long followers,
    Long following,
    String created_at,
    String updated_at
) {
    
}
