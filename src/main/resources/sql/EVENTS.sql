USE bankcentric;
SET GLOBAL event_scheduler = ON;

DROP EVENT IF EXISTS `yearly_delete_stale_audit_rows`;
DELIMITER $$
CREATE DEFINER =`root`@`localhost` EVENT yearly_delete_stale_audit_rows
  ON SCHEDULE
    EVERY 1 YEAR
  DO
  BEGIN
    DELETE FROM audit_log WHERE datetime < NOW() - INTERVAL 1 YEAR;
  END $$
DELIMITER ;