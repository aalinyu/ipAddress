package org.aalin.common.ip.util;

import java.io.Serializable;
import java.util.*;

/**
 * Queue 接口的大小可变数组的实现。实现了所有可选队列操作。除了实现 队列 接口外，此类还提供一些方法来操作内部用来存储队列的数组的大小。
 * <p>
 * 在添加大量元素前，应用程序可以使用 ensureCapacity 操作来增加 ArrayQueue 实例的容量。这可以减少递增式再分配的数量。
 *
 * <strong>该实现不是同步的。</strong>
 * <p>
 * 此类的 iterator 方法返回的迭代器是快速失败 的：在创建迭代器之后，如果对队列进行修改，除非通过迭代器自身的 remove
 * 方法，否则在任何时间以任何方式对其进行修改，Iterator 都将抛出
 * ConcurrentModificationException。因此，面对并发的修改
 * ，迭代器很快就会完全失败，而不冒将来在某个不确定时间发生任意不确定行为的风险。
 * <p>
 * 注意，迭代器的快速失败行为无法得到保证，因为一般来说，不可能对是否出现不同步并发修改做出任何硬性保证。快速失败迭代器在尽最大努力抛出
 * ConcurrentModificationException
 * 。因此，为提高这类迭代器的正确性而编写一个依赖于此异常的程序是错误做法：迭代器的快速失败行为应该仅用于检测程序错误。
 */
public class ArrayQueue<E> extends AbstractQueue<E> implements Queue<E>, Serializable, Cloneable {

    /**
     * 版本号
     */
    private static final long serialVersionUID = 67123573217451881L;

    private ArrayList<E> arrayList;

    private int size = 0;

    private int startIndex = 0;

    private int endIndex;

    private int modCount = 0;

    /**
     * 构造一个初始容量为 10 的空队列。
     */
    public ArrayQueue() {
        this.arrayList = new ArrayList<E>();
    }

    /**
     * 构造一个包含指定 collection 的元素的队列，这些元素是按照该 collection 的迭代器返回它们的顺序排列的。
     *
     * @param collection 其元素将放置在此队列中的 collection。
     * @throws NullPointerException 如果指定的 collection 为 null。
     */
    public ArrayQueue(Collection<? extends E> collection) {
        if (collection == null) {
            throw new NullPointerException();
        }
        this.arrayList = new ArrayList<E>(collection.size());
        for (E e : collection) {
            offer(e);
        }
        modCount = 0;
    }

    /**
     * 构造一个具有指定初始容量的空队列。
     *
     * @param initialCapacity 队列的初始容量。
     * @throws IllegalArgumentException 如果指定的初始容量为负。
     */
    public ArrayQueue(int initialCapacity) {
        this.arrayList = new ArrayList<E>(initialCapacity);
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayQueueIterator();
    }

    class ArrayQueueIterator implements Iterator<E> {

        private int cursor = 0;

        private int lastRet = -1;

        private int expectedModCount = modCount;

        public boolean hasNext() {
            return this.cursor != size();
        }

        public E next() {
            checkForComodification();
            try {
                E next = ArrayQueue.this.arrayList.get(this.cursor);
                this.lastRet = this.cursor++;
                return next;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            if (this.lastRet == -1) {
                throw new IllegalStateException();
            }
            checkForComodification();
            try {
                ArrayQueue.this.remove(this.lastRet);
                if (this.lastRet < this.cursor) {
                    this.cursor--;
                }
                this.lastRet = -1;
                this.expectedModCount = ArrayQueue.this.modCount;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (ArrayQueue.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    public boolean offer(E o) {
        if (o != null) {
            this.arrayList.add(o);
            this.size++;
            this.endIndex++;
            this.modCount++;
            return true;
        }
        return false;
    }

    public E peek() {
        if (hasNext()) {
            E element = this.arrayList.get(this.startIndex);
            return element;
        }
        return null;
    }

    public E poll() {
        if (hasNext()) {
            E element = this.arrayList.get(this.startIndex);
            this.arrayList.set(this.startIndex, null);
            this.startIndex++;
            this.size--;
            this.modCount++;
            return element;
        }
        return null;
    }

    @Override
    public void clear() {
        this.arrayList.clear();
        this.endIndex = 0;
        this.size = 0;
        this.startIndex = 0;
        this.modCount++;
    }

    private boolean hasNext() {
        return (this.startIndex >= this.endIndex) ? false : true;
    }

    /**
     * 将此 ArrayQueue 实例的容量调整为集的当前大小。应用程序可以使用此操作来最小化 ArrayQueue 实例的存储量。
     */
    public void trimToSize() {
        this.arrayList.trimToSize();
    }

    /**
     * 返回此 ArrayQueue 实例的浅表复制。（不复制这些元素本身） 。
     *
     * @return 此 ArrayQueue 实例的一个克隆。
     */
    @SuppressWarnings("unchecked")
    @Override
    public ArrayQueue<E> clone() {
        ArrayQueue<E> clone;
        try {
            clone = (ArrayQueue<E>) super.clone();
            clone.arrayList = (ArrayList<E>) this.arrayList.clone();
        } catch (Exception e) {
            throw new InternalError();
        }
        return clone;
    }
}
