package de.haw.vs.escr.boards.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Eric on 25.05.2016.
 */
//@Entity
//@Table(name = "Roll")
public class Roll {

    //@Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    int rollId;

    @Expose
    //@Column(name = "Number")
    int number;
}
