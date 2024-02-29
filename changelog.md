# Unreleased

# 3.0.2

- Create placeholder: ``%zkoth_capture_max_seconds%``
- Create placeholder: ``%zkoth_capture_max_formats%``
- Create placeholder: ``%zkoth_score``
- Create placeholder: ``%zkoth_score_player_<position>``
- Create placeholder: ``%zkoth_score_points_<position>``
- Create placeholder: ``%zkoth_score_team_id_<position>``
- Create placeholder: ``%zkoth_score_team_name_<position>``
- Create placeholder: ``%zkoth_score_team_leader_<position>``
- Create koth option: ``blacklistTeamId``
- Change TeamKoth interface to use OfflinePlayer instance of Player

# 3.0.1

- Add SaberFaction and FactionUUID support
- Add command ``/koth addcommand <koth name> <start/win> <command>``
- Add comment in file koth-example.yml
- Add placeholders:
1. ``%zkoth_team_id%`` - Current koth capture player team id
2. ``%zkoth_team_leader%`` - Current koth capture player team leader name
3. ``%zkoth_active_<koth name>%`` - Lets you know if a koth is active
4. ``%zkoth_cooldown_<koth name>%`` - Lets you know if a koth is in cooldown
5. ``%zkoth_start_<koth name>%`` - Lets you know if a koth is start
- Fix scoreboard and hologram update

# 3.0.0

New version completely recoded