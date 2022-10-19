SELECT TOP 1 ECE.ECO_RECIBO, * FROM FPG_REGISTROS_ESOCIAL_S_1210 FE
	INNER JOIN ESOCIAL_CONTROLA_ENVIO ECE
	ON FE.PES_COD = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-1210' 
	AND ECE.ECO_ANO = FE.COM_ANO AND  ECE.ECO_MES = FE.MES_COD

WHERE FE.cpfBenef = '52970086549' AND FE.perApur = '2022-08'
------------------------------------------------------------
SELECT ECO_RECIBO, * FROM ESOCIAL_CONTROLA_ENVIO WHERE ETA_COD = 'S-1210' AND ECO_CHAVE = 772
--------------------------------------------------------------
UPDATE ESOCIAL_CONTROLA_ENVIO 
SET ECO_RECIBO = NULL, ECO_SITUACAO = 'I'
FROM FPG_REGISTROS_ESOCIAL_S_1210 FE
INNER JOIN ESOCIAL_CONTROLA_ENVIO ECE
ON FE.PES_COD = ECE.ECO_CHAVE AND ECE.ETA_COD = 'S-1210'
AND ECE.ECO_ANO = FE.COM_ANO AND  ECE.ECO_MES = FE.MES_COD

WHERE FE.cpfBenef = '52970086549' AND FE.perApur = '2022-08'



