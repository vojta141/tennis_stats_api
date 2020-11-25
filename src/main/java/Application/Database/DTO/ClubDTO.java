package Application.Database.DTO;

import Application.Database.Enity.Club;

public class ClubDTO {
    private int id;
    private String name;

    public ClubDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof ClubDTO))
            return false;
        else{
            ClubDTO clubDTO = (ClubDTO) o;
            if(this.id == clubDTO.id &&
                this.name.equals(clubDTO.name))
                return true;
        }
        return false;
    }
}
