package com.github.anastr.rxlab.controllers

import com.github.anastr.rxlab.objects.drawing.FixedEmitsOperation
import com.github.anastr.rxlab.objects.drawing.ObserverObject
import com.github.anastr.rxlab.objects.emits.BallEmit
import com.github.anastr.rxlab.preview.OperationController
import com.github.anastr.rxlab.view.Action
import io.reactivex.rxjava3.core.Observable

/**
 * Created by Anas Altair on 4/1/2020.
 */
class FromArrayController: OperationController() {

    override fun onCreate() {
        setCode("String[] array = {\"A\", \"r\", \"r\", \"a\", \"y\"};\n" +
                "Observable.fromArray(array)\n" +
                "        .subscribe();")


        val array = listOf(
            BallEmit("A"),
            BallEmit("r"),
            BallEmit("r"),
            BallEmit("a"),
            BallEmit("y")
        )

        val fromArrayOperation = FixedEmitsOperation("fromArray", array)
        surfaceView.addDrawingObject(fromArrayOperation)
        val observerObject = ObserverObject("Observer")
        surfaceView.addDrawingObject(observerObject)

        val actions = ArrayList<Action>()

        Observable.fromIterable(array)
            .subscribe({
                actions.add(Action(1000) { moveEmitOnRender(it, observerObject) })
            }, errorHandler, {
                actions.add(Action(0) { doOnRenderThread { observerObject.complete() } })
                surfaceView.actions(actions)
            })
            .disposeOnDestroy()
    }

}