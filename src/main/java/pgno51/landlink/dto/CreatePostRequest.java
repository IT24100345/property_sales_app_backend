package pgno51.landlink.dto;

import lombok.Data;

import pgno51.landlink.model.PropertyType;
import lombok.Data;

@Data
public class CreatePostRequest {
    private Integer landArea;
    private Integer views;
    private Integer inquiries;
    private Float price;
    private String title;
    private String description;
    private String location;
    private String contactPhone;
    private String[] images;
    private String[] features;
    private PropertyType type;
    private String verificationStatus;
    private Integer authorId; // optional: if null, controller will use authenticated user
}

