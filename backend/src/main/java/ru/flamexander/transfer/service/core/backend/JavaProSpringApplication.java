package ru.flamexander.transfer.service.core.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// -Dspring.profiles.active=dev

/*
	- Необходимо реализовать логику перевода средств между счетами:
		- Перевод осуществляется по номеру счета
		- Нельзя перевести средства на несуществующий счет
		- Нельзя перевести средств больше, чем есть на счете отправителя
		- Нельзя перевести отрицательное количество средств (затянуть к себе средства с чужого счета)
	- Все переводы должны сохраняться в БД:
	    - id,
	    - от кого - ид клиента-отправителя + внутренний ид счета,
	    - кому - ид клиента-получателя + номер счета получателя,
	    - сумма,
	    - статус (создан, в обработке, выполнен, ошибка),
	    - дата создания/последнего апдейта
	- Должна быть возможность посмотреть список своих переводов
*/

@SpringBootApplication
public class JavaProSpringApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaProSpringApplication.class, args);
	}
}
