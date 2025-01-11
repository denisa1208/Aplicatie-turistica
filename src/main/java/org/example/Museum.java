package org.example;

import org.example.Location;

import javax.xml.crypto.Data;


public class Museum {
    // design pattern - builder pattern

    private final String name;
    private final long code;
    private final long supervisorCode;
    private final Location location;

    // campuri optionale
    private final Person manager;
    private final Integer foundingYear;
    private final String phoneNumber;
    private final String fax;
    private final String email;
    private final String url;
    private final String profile;
    private String category;
    private final int error; // 0-no 1-yes



    private Museum(MuseumBuilder builder) {
        this.name = builder.name;
        this.code = builder.code;
        this.supervisorCode = builder.supervisorCode;
        this.location = builder.location;
        this.manager = builder.manager;
        this.foundingYear = builder.foundingYear;
        this.phoneNumber = builder.phoneNumber;
        this.fax = builder.fax;
        this.email = builder.email;
        this.url = builder.url;
        this.profile = builder.profile;
        this.category = builder.category;
        this.error = builder.error;
    }



    public static class MuseumBuilder {
        private String name;
        private long code;
        private long supervisorCode;
        private Location location;
        private int err;

        private Person manager;
        private Integer foundingYear;
        private String phoneNumber;
        private String fax;
        private String email;
        private String url;
        private String profile;
        private String category;
        private int error;

        public int getErr() {
            return err;
        }

        public MuseumBuilder(String name, long code, long supervisorCode, Location location) {
            this.name = name;
            this.code = code;
            this.supervisorCode = supervisorCode;
            this.location = location;
        }



        public MuseumBuilder setManager(Person manager) {
            this.manager = manager;
            return this;
        }

        public MuseumBuilder setFoundingYear(Integer foundingYear) {
            this.foundingYear = foundingYear;
            return this;
        }

        public MuseumBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public MuseumBuilder setFax(String fax) {
            this.fax = fax;
            return this;
        }

        public MuseumBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public MuseumBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public MuseumBuilder setProfile(String profile) {
            this.profile = profile;
            return this;
        }

        public MuseumBuilder setCategory(String category) {
            this.category = category;
            return this;
        }

        public MuseumBuilder setError(int error) {
            this.error = error;
            return this;
        }

        public int getError() {
            return error;
        }

        public Museum build() {
            return new Museum(this);
        }
    }

    public String getName() {
        return name;
    }

    public long getCode() {
        return code;
    }

    public long getSupervisorCode() {
        return supervisorCode;
    }

    public Location getLocation() {
        return location;
    }

    public Person getManager() {
        return manager;
    }

    public Integer getFoundingYear() {
        return foundingYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getUrl() {
        return url;
    }

    public String getProfile() {
        return profile;
    }

    public String getCategory() {
        return category;
    }

}

class MuseumCommands {
    public void addMuseum(String line, Database database) {
        Museum museum = null;
            String[] parts = line.split("\\|");
            if (parts.length > 0) {
                System.out.println("Comandă: " + parts[0]);

                String code_string = parts[1];
                long code = Long.parseLong(code_string);
                String name = parts[2];
                String county = parts[3];
                String locality = parts[4];
                String adminUnit = parts[5];
                String address = parts[6];
                String cod_postal = parts[7];
                String phone = parts[8];
                String fax = parts[9];
                String foundingYear = parts[10];
                Integer foundingYear_parse = 0;
                if (!foundingYear.equals("")) {
                    foundingYear_parse = Integer.valueOf(foundingYear);
                }

                String url = parts[11];
                String email = parts[12];
                String manager_name = parts[13];
                String surname = null;
                String name_m = null;
                if (!manager_name.equals("")) {
                    String[] nameParts = manager_name.split(" ");
                    surname = nameParts[1];
                    name_m = nameParts[0];
                }


                String cod_institution_coord = parts[14];
                long cod_institution = Long.parseLong(cod_institution_coord);
                String principle_profile = parts[15];
                String sirutaCode = parts[16];
                Integer sirutaCode_parse = Integer.valueOf(sirutaCode);
                String category = parts[17];
                String latitude = parts[18];
                latitude = latitude.replace(",", "");
                String longitude = parts[19];
                longitude = longitude.replace(",", "");
                Integer latitude_parse = Integer.valueOf(latitude);
                Integer longitude_parse = Integer.valueOf(longitude);

                try {

                    Location location = new Location(county, sirutaCode_parse, locality, adminUnit, address, latitude_parse, longitude_parse);
                    Person manager = new Person(surname, name_m, manager_name);
                    museum = new Museum.MuseumBuilder(name, code, cod_institution, location)
                            .setFoundingYear(foundingYear_parse)
                            .setCategory(category)
                            .setFax(fax)
                            .setEmail(email)
                            .setManager(manager)
                            .setProfile(principle_profile)
                            .setUrl(url)
                            .setPhoneNumber(phone)
                            .setError(0).build();
                    System.out.println(museum.getName() + "muzeul ahahah");


                    database.addMuseum(museum);

                } catch (Exception e) {
                    System.out.println("ana are mere");
                    throw new RuntimeException(e);
                }


            }

    }
}
