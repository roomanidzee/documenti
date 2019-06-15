import { Component, Vue } from 'vue-property-decorator';
import WithRender from './to-do.html';

import ToDoForm from '../to-do-form/ToDoForm';

import Task from '@/types/Task';

@WithRender
@Component({
    components: {
        'to-do-form': ToDoForm,
    },
})
export default class ToDo extends Vue {

    public tasks: Task[] = [
        { description: 'Make Coffee', completed: false },
        { description: 'Feed Dragons', completed: false },
    ];

    public addTask(description: string): void {
        this.tasks.push({ description, completed: false });
    }

}
