package fr.rbo.elitweb.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;


public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoqueur, Response reponse) {

        if(reponse.status() == 409 ) {
            return new ConflictException("Erreur : 409 ");
        }
        if(reponse.status() == 406 ) {
            return new NotAcceptableException("Erreur : 406 ");
        }
        if(reponse.status() == 404 ) {
            return new NotFoundException("Erreur : 404 ");
        }
        if(reponse.status() == 400 ) {
            return new BadRequestException("Erreur : 400 ");
        }

        return defaultErrorDecoder.decode(invoqueur, reponse);
    }

}
