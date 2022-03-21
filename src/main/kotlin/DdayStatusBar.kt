import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.openapi.wm.WindowManager
import com.intellij.util.Consumer
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.awt.Component
import java.awt.event.MouseEvent
import java.util.*
import kotlin.time.Duration

class DdayStatusBarWidgetPresentation(private val statusBar: StatusBar) : StatusBarWidget.TextPresentation {

    companion object {
        private val date = Instant.parse("2021-10-11T00:00:00Z")
    }

    private fun calculateDuration(): Duration {
        return Clock.System.now() - date
    }

    override fun getText(): String {
        return "D-${calculateDuration().inWholeDays + 1}"
    }

    override fun getAlignment() = Component.CENTER_ALIGNMENT

    override fun getTooltipText() = text

    override fun getClickConsumer(): Consumer<MouseEvent> {
        return Consumer { event -> statusBar.updateWidget(Globals.WidgetId) }
    }
}

class DdayStatusBarWidget(project: Project) : StatusBarWidget {

    private var timer: Timer? = null
    private var statusBar: StatusBar

    override fun dispose() {
        timer?.cancel()
        timer = null
    }

    override fun ID() = Globals.WidgetId

    override fun getPresentation(): StatusBarWidget.WidgetPresentation =
        DdayStatusBarWidgetPresentation(statusBar)

    override fun install(statusBar: StatusBar) {
        ApplicationManager.getApplication().executeOnPooledThread { startTimer(statusBar) }
    }

    private fun startTimer(statusBar: StatusBar) {
        runCatching {
            timer = Timer()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    statusBar.updateWidget(Globals.WidgetId)
                }
            }, 0, 1000 * 60)
        }.onFailure {
            it.printStackTrace()
        }
    }

    init {
        statusBar = WindowManager.getInstance().getStatusBar(project)
    }
}

class DdayStatusBarWidgetFactory : StatusBarWidgetFactory {

    override fun getId(): String = Globals.WidgetId

    override fun getDisplayName() = Globals.WidgetName

    override fun isAvailable(project: Project): Boolean = true

    override fun createWidget(project: Project): StatusBarWidget {
        return DdayStatusBarWidget(project)
    }

    override fun disposeWidget(widget: StatusBarWidget) {

    }

    override fun canBeEnabledOn(statusBar: StatusBar): Boolean {
        return true
    }
}