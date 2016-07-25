package com.example.arnauddupeyrat.Animalis.Model;

import com.example.arnauddupeyrat.Animalis.Model.DTO.ProfileUserDto;

/**
 * Created by arnauddupeyrat on 17/06/16.
 */
public class Model {

    private ProfileUserDto profileUser;
    private String accesToken;

    public Model(){
        profileUser = new ProfileUserDto();
    }

    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }

    public ProfileUserDto getProfile() {
        return profileUser;
    }

    public void setProfile(ProfileUserDto profile) {
        this.profileUser = profile;
    }


}
