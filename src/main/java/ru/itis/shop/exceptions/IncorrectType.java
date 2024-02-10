package ru.itis.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 10.05.2021
 * shop-service
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectType extends RuntimeException {
}
