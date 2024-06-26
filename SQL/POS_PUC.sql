CREATE TABLE ITEM (
    ID NUMBER(10) PRIMARY KEY,
    NOME VARCHAR2(255 CHAR) NOT NULL,
    QUANTIDADE NUMBER NOT NULL,
    VALOR NUMBER(10, 2) NOT NULL,
    DESCRITIVO VARCHAR2(255)
);

SELECT * FROM ITEM;

CREATE SEQUENCE SEQ_ITEM_AI 
    START WITH 1 
    INCREMENT BY 1
    MAXVALUE 99999999999;
    
CREATE OR REPLACE TRIGGER TRG_ITEM_AI
    BEFORE INSERT ON ITEM
    FOR EACH ROW
    BEGIN
        IF :NEW.ID IS NULL THEN
            SELECT SEQ_ITEM_AI.NEXTVAL INTO :NEW.ID FROM DUAL;
        END IF;
END;    

INSERT INTO ITEM (NOME, QUANTIDADE, VALOR, DESCRITIVO) VALUES ('Caneta Esferográfica', 100, 1.50, 'Caneta esferográfica azul, ponta média de 1.0 mm');

INSERT INTO ITEM (NOME, QUANTIDADE, VALOR, DESCRITIVO) VALUES ('Caderno Universitário', 50, 20.90, 'Caderno universitário com 200 folhas pautadas, capa dura');

INSERT INTO ITEM (NOME, QUANTIDADE, VALOR, DESCRITIVO) VALUES ('Pacote de Post-it', 75, 5.00, 'Pacote com 4 blocos de Post-it, 50 folhas cada, cores variadas');
