import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    private val dividerHeight = 1 // 선의 두께 (dp)
    private val dividerColor = ContextCompat.getColor(context, android.R.color.darker_gray)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = dividerHeight // 각 항목의 아래쪽에 선의 두께만큼 공간 추가
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        val width = parent.width

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + dividerHeight

            // 선 그리기
            canvas.drawRect(0f, top.toFloat(), width.toFloat(), bottom.toFloat(), Paint().apply {
                color = dividerColor
                style = Paint.Style.FILL
            })
        }
    }
}
