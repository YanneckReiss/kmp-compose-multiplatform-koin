import androidx.compose.ui.window.ComposeUIViewController
import com.yanneckreiss.kotlintutorial.shared.App
import com.yanneckreiss.kotlintutorial.shared.SharedModule
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin() {
    startKoin {
        modules(SharedModule().module)
    }
}
