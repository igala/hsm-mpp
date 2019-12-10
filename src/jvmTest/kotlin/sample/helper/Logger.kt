package sample.helper

import de.artcom.hsm.ILogger
import java.util.logging.Level
import java.util.logging.Logger

class Logger : ILogger {
    override fun debug(message: String?) {
        Logger.getAnonymousLogger().log(Level.ALL, message)
    }
}