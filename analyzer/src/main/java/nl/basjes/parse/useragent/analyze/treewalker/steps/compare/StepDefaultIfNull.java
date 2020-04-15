/*
 * Yet Another UserAgent Analyzer
 * Copyright (C) 2013-2020 Niels Basjes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.basjes.parse.useragent.analyze.treewalker.steps.compare;

import nl.basjes.parse.useragent.analyze.treewalker.steps.Step;
import nl.basjes.parse.useragent.analyze.treewalker.steps.WalkList.WalkResult;
import nl.basjes.parse.useragent.parse.MatcherTree;
import org.antlr.v4.runtime.tree.ParseTree;

public class StepDefaultIfNull extends Step {

    private final String  defaultValue;
    private final boolean canFail;

    @SuppressWarnings("unused") // Private constructor for serialization systems ONLY (like Kryo)
    private StepDefaultIfNull() {
        defaultValue = "<< Should not be seen anywhere >>";
        canFail = false;
    }

    public StepDefaultIfNull(String defaultValue) {
        this.defaultValue = defaultValue;
        canFail = defaultValue == null;
    }

    @Override
    public boolean canFail() {
        return canFail;
    }

    @Override
    public boolean mustHaveInput() {
        return canFail;
    }

    @Override
    public WalkResult walk(ParseTree<MatcherTree> tree, String value) {
        WalkResult actualValue = walkNextStep(tree, value);

        if (actualValue == null ||
            actualValue.getValue() == null) {
            return new WalkResult(tree, defaultValue);
        }
        return actualValue;
    }

    @Override
    public String toString() {
        return "DefaultIfNull(default=" + defaultValue + ")";
    }

}
