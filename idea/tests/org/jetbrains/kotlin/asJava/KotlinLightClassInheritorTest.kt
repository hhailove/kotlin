/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.asJava

import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.testFramework.LightProjectDescriptor
import org.jetbrains.kotlin.idea.test.JetLightCodeInsightFixtureTestCase
import org.jetbrains.kotlin.idea.test.JetLightProjectDescriptor
import org.jetbrains.kotlin.psi.JetClass
import org.jetbrains.kotlin.psi.JetFile
import org.junit.Assert

public class KotlinLightClassInheritorTest : JetLightCodeInsightFixtureTestCase() {
    public fun testAnnotation() {
        doTestInheritorByText("annotation class A", "java.lang.annotation.Annotation", false)
    }

    public fun testEnum() {
        doTestInheritorByText("enum class A", "java.lang.Enum", false)
    }

    public fun testIterable() {
        doTestInheritorByText("abstract class A<T> : Iterable<T>", "java.lang.Iterable", false)
    }

    public fun testIterableDeep() {
        doTestInheritorByText("abstract class A<T> : List<T>", "java.lang.Iterable", true)
    }

    public fun testObjectDeep() {
        doTestInheritorByText("abstract class A<T> : List<T>", "java.lang.Object", true)
    }

    private fun doTestInheritorByText(text: String, superQName: String, checkDeep: Boolean) {
        val file = myFixture.configureByText("A.kt", text) as JetFile
        val jetClass = file.declarations.filterIsInstance<JetClass>().single()
        val psiClass = LightClassGenerationSupport.getInstance(project).getPsiClass(jetClass)!!
        val baseClass = JavaPsiFacade.getInstance(project).findClass(superQName, GlobalSearchScope.allScope(project))!!
        Assert.assertTrue(psiClass.isInheritor(baseClass, checkDeep))
    }

    override fun getProjectDescriptor(): LightProjectDescriptor = JetLightProjectDescriptor.INSTANCE
}
