package fr.rbo.elitapi.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Reservation {
    private static final Logger log = LoggerFactory.getLogger(Reservation.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long reservationId;
    @ManyToOne
    private Ouvrage ouvrage;
    @ManyToOne
    private User user;
    private Date reservationDateDemande;
    private Date reservationDateNotif;
    @NotNull
    private Boolean reservationActive;
}
